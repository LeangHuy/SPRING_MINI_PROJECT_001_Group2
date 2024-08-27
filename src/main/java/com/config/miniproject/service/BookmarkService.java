package com.config.miniproject.service;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.CommentWithArticleResponse;
import com.config.miniproject.model.entity.Bookmark;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookmarkService {

    Bookmark createBookmark(Integer ArticleId);

    List<ArticleResponse> getAllBookmarks(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection);

    void updateBookmarked(Integer id);
}
