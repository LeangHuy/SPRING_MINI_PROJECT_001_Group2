package com.config.miniproject.repository;


import com.config.miniproject.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {

    Bookmark findBookmarkByArticleIdAndUserId (Integer articleId, Integer userId);
}
