package com.laptechnos.groupface.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.laptechnos.groupface.domain.Caste;
import com.laptechnos.groupface.repository.CasteRepository;
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
 * Spring Data Elasticsearch repository for the {@link Caste} entity.
 */
public interface CasteSearchRepository extends ElasticsearchRepository<Caste, Long>, CasteSearchRepositoryInternal {}

interface CasteSearchRepositoryInternal {
    Page<Caste> search(String query, Pageable pageable);

    Page<Caste> search(Query query);

    void index(Caste entity);
}

class CasteSearchRepositoryInternalImpl implements CasteSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final CasteRepository repository;

    CasteSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, CasteRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<Caste> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<Caste> search(Query query) {
        SearchHits<Caste> searchHits = elasticsearchTemplate.search(query, Caste.class);
        List<Caste> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(Caste entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
