package br.com.axur.axurcrawling.repository;

import br.com.axur.axurcrawling.model.CrawEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawRepository extends CrudRepository<CrawEntity, Long> {


}
