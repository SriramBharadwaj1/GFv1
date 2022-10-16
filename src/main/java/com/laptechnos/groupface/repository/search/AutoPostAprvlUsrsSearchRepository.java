package com.laptechnos.groupface.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.laptechnos.groupface.domain.AutoPostAprvlUsrs;
import com.laptechnos.groupface.repository.AutoPostAprvlUsrsRepository;
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
 * Spring Data Elasticsearch repository for the {@link AutoPostAprvlUsrs} entity.
 */
public interface AutoPostAprvlUsrsSearchRepository
    extends ElasticsearchRepository<AutoPostAprvlUsrs, Long>, AutoPostAprvlUsrsSearchRepositoryInternal {}

interface AutoPostAprvlUsrsSearchRepositoryInternal {
    Page<AutoPostAprvlUsrs> search(String query, Pageable pageable);

    Page<AutoPostAprvlUsrs> search(Query query);

    void index(AutoPostAprvlUsrs entity);
}

class AutoPostAprvlUsrsSearchRepositoryInternalImpl implements AutoPostAprvlUsrsSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final AutoPostAprvlUsrsRepository repository;

    AutoPostAprvlUsrsSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, AutoPostAprvlUsrsRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<AutoPostAprvlUsrs> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<AutoPostAprvlUsrs> search(Query query) {
        SearchHits<AutoPostAprvlUsrs> searchHits = elasticsearchTemplate.search(query, AutoPostAprvlUsrs.class);
        List<AutoPostAprvlUsrs> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(AutoPostAprvlUsrs entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
