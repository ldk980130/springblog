package springpractice.springblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;
import springpractice.springblog.repository.spd.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category resister(Blog blog, String name) {

        validateSameNameFromBlog(blog, name);
        Category category = Category.create(blog, name);
        categoryRepository.save(category);

        return category;
    }

    private void validateSameNameFromBlog(Blog blog, String name) {
        if (categoryRepository.findByBlogAndName(blog, name).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
    }

    @Transactional
    public void editName(Long categoryId, String name) {

        Category category = categoryRepository.findById(categoryId).get();

        validateSameNameFromBlog(category.getBlog(), name);
        category.editName(name);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void delete(Category category) {
        category.removeRelations();
        categoryRepository.delete(category);
    }


}
