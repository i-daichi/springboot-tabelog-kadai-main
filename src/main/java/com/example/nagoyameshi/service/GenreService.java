package com.example.nagoyameshi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Genre;
import com.example.nagoyameshi.repository.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // ジャンル一覧を取得
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    // IDでジャンルを取得
    public Optional<Genre> getGenreById(Integer id) {
        return genreRepository.findById(id);
    }

    // ジャンルを追加
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // ジャンルを更新
    public Genre updateGenre(Integer id, Genre genre) {
        if (genreRepository.existsById(id)) {
            genre.setId(id);
            return genreRepository.save(genre);
        } else {
            throw new IllegalArgumentException("ジャンルが存在しません");
        }
    }

    // ジャンルを削除
    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }
}
