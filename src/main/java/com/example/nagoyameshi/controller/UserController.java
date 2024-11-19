package com.example.nagoyameshi.controller;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.form.UserEditPaidForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserRepository userRepository; // Userリポジトリ
	private final UserService userService; // ユーザー関連サービス

	// コンストラクタでUserRepositoryとUserServiceをDI
	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	// ユーザー情報を表示するインデックスページ
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userRepository.findUserById(userDetailsImpl.getUser().getId()); // ログインユーザーの情報を取得

		model.addAttribute("user", user); // ユーザー情報をビューに渡す

		return "user/show"; // user/indexテンプレートを返す
	}

	// ユーザー情報編集ページの表示
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		// ユーザー情報を取得し、UserEditFormにセット
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(),
				user.getPostalCode(), user.getAddress(), user.getPhoneNumber(), user.getEmail());

		model.addAttribute("userEditForm", userEditForm); // 編集フォームにユーザー情報を渡す

		return "user/edit"; // user/editテンプレートを返す
	}

	// ユーザー情報更新処理
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// メールアドレスが変更されており、かつすでに登録されている場合、エラーメッセージを追加
		if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
			bindingResult.addError(
					new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。"));
		}

		// バリデーションエラーがあればフォームを再表示
		if (bindingResult.hasErrors()) {
			return "user/edit";
		}

		// ユーザー情報の更新処理
		userService.update(userEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");

		return "redirect:/user"; // 更新後にユーザーの詳細ページにリダイレクト
	}

	// 無料会員から有料会員への変更画面に遷移
	@GetMapping("/changepaid")
	public String changepaid(
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model,
			RedirectAttributes redirectAttributes) {
		// ログイン中のユーザー情報を元にUserEditPaidFormを作成
		UserEditPaidForm up = new UserEditPaidForm(userDetailsImpl.getUser().getId());

		model.addAttribute("userEditPaidForm", up); // フォームデータをビューに渡す

		return "user/changepaid"; // 有料会員変更画面を返す
	}

	// 有料会員から無料会員への変更画面に遷移
	@GetMapping("/changefree")
	public String changefree(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		// ログイン中のユーザー情報を元にUserEditPaidFormを作成
		UserEditPaidForm up = new UserEditPaidForm(userDetailsImpl.getUser().getId());
		model.addAttribute("userEditPaidForm", up); // フォームデータをビューに渡す

		return "user/changefree"; // 無料会員変更画面を返す
	}

	// 会員ステータス（有料/無料）変更処理
	@PostMapping("/editpaid")
	public String editPaid(
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute @Validated UserEditPaidForm userEditPaidForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {
		System.out.println("editpaid######" + userDetailsImpl.getUser().getId()); // デバッグ用ログ
		System.out.println("bindingResult.hasErrors():" + bindingResult); // デバッグ用ログ

		// バリデーションエラーがあれば有料会員変更画面に戻す
		if (bindingResult.hasErrors()) {
			return "user/changepaid";
		}

		// ユーザーの会員ステータスを変更（無料から有料、またはその逆）
		User updatedUser = userService.updatePaid(userDetailsImpl.getUser().getId());
		System.out.println(userDetailsImpl.getAuthorities());
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(updatedUser.getRole().getName());

		// 新しいUserDetailsImplを作成
		UserDetailsImpl updatedUserDetails = new UserDetailsImpl(updatedUser, authorities);

		// 新しい認証情報を作成
		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
				updatedUserDetails, // 新しいUserDetails
				null, // パスワードはnullでもOK（すでに認証されているため）
				updatedUserDetails.getAuthorities() // 新しいAuthorities
		);

		SecurityContextHolder.getContext().setAuthentication(newAuth);

		if (updatedUser.getRole().getId() == 3) {
			redirectAttributes.addFlashAttribute("successMessage", "有料プランへの登録が完了しました。");
		} else {
			redirectAttributes.addFlashAttribute("successMessage", "無料プランへの変更が完了しました。");
		}

		return "redirect:/";
	}

	// 企業情報ページ
	@GetMapping("/company")
	public String company() {
		return "auth/company"; // 企業情報ページを返す
	}

	// サブスクリプション（有料会員）ページ
	@GetMapping("/subscription")
	public String subscription() {
		return "user/subscription"; // サブスクリプションページを返す
	}
}
