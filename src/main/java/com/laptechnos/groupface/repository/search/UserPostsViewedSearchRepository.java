package com.laptechnos.groupface.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.laptechnos.groupface.domain.UserPostsViewed;
import com.laptechnos.groupface.repository.UserPostsViewedRepository;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data Elasticsearch repository for the {@link UserPostsViewed} entity.
 */
public interface UserPostsViewedSearchRepository
    extends ElasticsearchRepository<UserPostsViewed, Long>, UserPostsViewedSearchRepositoryInternal {}

interface UserPostsViewedSearchRepositoryInternal {
    Page<UserPostsViewed> search(String query, Pageable pageable);

    Page<UserPostsViewed> search(Query query);

    void index(UserPostsViewed entity);
}

class UserPostsViewedSearchRepositoryInternalImpl implements UserPostsViewedSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final UserPostsViewedRepository repository;

    UserPostsViewedSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, UserPostsViewedRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<UserPostsViewed> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<UserPostsViewed> search(Query query) {
        SearchHits<UserPostsViewed> searchHits = elasticsearchTemplate.search(query, UserPostsViewed.class);
        List<UserPostsViewed> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(UserPostsViewed entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
