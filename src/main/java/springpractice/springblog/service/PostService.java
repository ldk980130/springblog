package springpractice.springblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springpractice.springblog.domain.Blog;
import springpractice.springblog.domain.Category;
import springpractice.springblog.domain.Post;
import springpractice.springblog.repository.spd.CategoryRepository;
import springpractice.springblog.repository.spd.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post resister(Category category, String title, String content) {

        Post post = Post.create(category, title, content);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public void edit(Long postId, String title, String content) {
        Post post = postRepository.findById(postId).get();
        post.edit(title, content);
    }

    public Post findOne(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Page<Post> findPageFromAllPosts(int start, int max) {
        PageRequest pageRequest = PageRequest.of(start, max, Sort.Direction.ASC, "createTime");
        return postRepository.findAll(pageRequest);
    }

    public List<Post> findAllByBlog(Blog blog) {;
        return postRepository.findAllByCategoryBlog(blog, Sort.by("createTime"));
    }

    public List<Post> findPageByBlog(Blog blog, int start, int max) {
        PageRequest pageRequest = PageRequest.of(start, max, Sort.Direction.ASC, "createTime");
        return postRepository.findAllByCategoryBlog(blog, pageRequest);
    }

    @Transactional
    public void moveToAnotherCategory(Long postId, Category category) {
        Post post = postRepository.findById(postId).get();
        post.changeCategory(category);
    }

    @Transactional
    public void delete(Post post) {
        post.removeRelations();
        postRepository.delete(post);
    }
}
