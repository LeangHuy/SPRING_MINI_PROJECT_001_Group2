package com.config.miniproject.repository;


import com.config.miniproject.model.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {

    Bookmark findBookmarkByArticleIdAndUserId (Integer articleId, Integer userId);
    Page<Bookmark> findAllByUserId(Pageable pageable, Integer userId);
    List<Bookmark> findAllByUserId(Integer userId);
}
