package ml.market.cors.repository.bookcategory;


import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book_Category_Repository extends JpaRepository<Book_CategoryDAO,Long> {
    
}
