package com.config.miniproject.service.serviceImpl;


import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.BookmarkResponse;
import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Bookmark;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.ArticleRepository;
import com.config.miniproject.repository.BookmarkRepository;
import com.config.miniproject.service.BookmarkService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ArticleRepository articleRepository;
    private final AppUserRepository appUserRepository;
    private final ArticleServiceImpl articleService;

    @Override
    public Bookmark createBookmark( Integer ArticleId) {
        Article article = articleRepository.findById(ArticleId).orElseThrow(
                () -> new NotFoundException("article" + ArticleId + "is not found")
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
    public List<ArticleResponse> getAllBookmarks(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Integer userId = GetCurrentUser.userId();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortBy));
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        List<BookmarkResponse> bookmarkPage = bookmarkRepository.findAllByUserId(pageable,userId).getContent().stream().map(Bookmark::toResponse).toList();
        for (BookmarkResponse bookmarkResponse : bookmarkPage) {
            ArticleResponse articleResponse = articleService.getArticleById(bookmarkResponse.getArticleId());
            articleResponse.setUpdatedAt(bookmarkResponse.getUpdatedAt()  );
            articleResponseList.add(articleResponse);
        }

        if(articleResponseList.isEmpty()){
            throw new NotFoundException("No bookmarked article found");
        }
        return articleResponseList;
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


