package com.config.miniproject.service.serviceImpl;


import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.BookmarkRequest;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Bookmark;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.ArticleRepository;
import com.config.miniproject.repository.BookmarkRepository;
import com.config.miniproject.service.BookmarkService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ArticleRepository articleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public Bookmark createBookmark( Integer ArticleId) {
        Article article = articleRepository.findById(ArticleId).orElseThrow(
                () -> new NotFoundException("article id is not found !!! ")
        );
         Bookmark bookmark = new Bookmark();
         bookmark.setArticle(article);
         bookmark.setUser(appUserRepository.getAppUserById(GetCurrentUser.userId()));
         bookmark.setStatus(true);
         bookmark.setCreatedAt(LocalDateTime.now());
         bookmark.setUpdatedAt(LocalDateTime.now());

        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Bookmark getAllBookmarks(Integer page, Integer size, String sortBy, Sort.Direction orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy, sortBy));
        Page<Bookmark> bookmarks = bookmarkRepository.findAll(pageable);
        return null;
    }

    @Override
    public void updateBookmarked(Integer id) {
        Integer userId = GetCurrentUser.userId();
        Bookmark bookmark = bookmarkRepository.findBookmarkByArticleIdAndUserId(id, userId);
        if(bookmark == null) {
            throw new NotFoundException("article id is not found !!! " + "or you might not the owner of this bookmark");
        }

        boolean currentStatus = bookmark.getStatus();
        bookmark.setStatus(!currentStatus);
        bookmarkRepository.save(bookmark);
    }
}


