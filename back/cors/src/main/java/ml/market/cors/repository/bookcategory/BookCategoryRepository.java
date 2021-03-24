package ml.market.cors.repository.bookcategory;


import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<Book_CategoryDAO,Long>,BookCategoryRepositoryCustom{
}
