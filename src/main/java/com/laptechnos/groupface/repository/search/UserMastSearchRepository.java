package com.laptechnos.groupface.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.repository.UserMastRepository;
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
 * Spring Data Elasticsearch repository for the {@link UserMast} entity.
 */
public interface UserMastSearchRepository extends ElasticsearchRepository<UserMast, Long>, UserMastSearchRepositoryInternal {}

interface UserMastSearchRepositoryInternal {
    Page<UserMast> search(String query, Pageable pageable);

    Page<UserMast> search(Query query);

    void index(UserMast entity);
}

class UserMastSearchRepositoryInternalImpl implements UserMastSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final UserMastRepository repository;

    UserMastSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, UserMastRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<UserMast> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<UserMast> search(Query query) {
        SearchHits<UserMast> searchHits = elasticsearchTemplate.search(query, UserMast.class);
        List<UserMast> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(UserMast entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
