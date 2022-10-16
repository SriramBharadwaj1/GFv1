package com.laptechnos.groupface.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.laptechnos.groupface.domain.OrgDetails;
import com.laptechnos.groupface.repository.OrgDetailsRepository;
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
 * Spring Data Elasticsearch repository for the {@link OrgDetails} entity.
 */
public interface OrgDetailsSearchRepository extends ElasticsearchRepository<OrgDetails, Long>, OrgDetailsSearchRepositoryInternal {}

interface OrgDetailsSearchRepositoryInternal {
    Page<OrgDetails> search(String query, Pageable pageable);

    Page<OrgDetails> search(Query query);

    void index(OrgDetails entity);
}

class OrgDetailsSearchRepositoryInternalImpl implements OrgDetailsSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final OrgDetailsRepository repository;

    OrgDetailsSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, OrgDetailsRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<OrgDetails> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<OrgDetails> search(Query query) {
        SearchHits<OrgDetails> searchHits = elasticsearchTemplate.search(query, OrgDetails.class);
        List<OrgDetails> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(OrgDetails entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
