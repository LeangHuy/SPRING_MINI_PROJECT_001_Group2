package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.BookmarkRequest;
import com.config.miniproject.model.entity.Bookmark;
import com.config.miniproject.repository.BookmarkRepository;
import org.springframework.data.domain.Sort;

public interface BookmarkService {

    Bookmark createBookmark(Integer ArticleId);

    Bookmark getAllBookmarks(Integer page, Integer size, String sortBy, Sort.Direction orderBy);

    void updateBookmarked(Integer id);
}
