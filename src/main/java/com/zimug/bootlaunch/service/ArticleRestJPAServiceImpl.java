package com.zimug.bootlaunch.service;

import com.zimug.bootlaunch.jpa.testdb.Article;
import com.zimug.bootlaunch.jpa.testdb.ArticleRepository;
import com.zimug.bootlaunch.jpa.testdb2.Message;
import com.zimug.bootlaunch.jpa.testdb2.MessageRepository;
import com.zimug.bootlaunch.model.ArticleVO;
import com.zimug.bootlaunch.utils.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArticleRestJPAServiceImpl implements ArticleRestService {

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private Mapper dozerMapper;

    public ArticleVO saveArticle( ArticleVO article) {

        Article articlePO = dozerMapper.map(article,Article.class);
        articleRepository.save(articlePO);

        Message message = new Message();
        message.setName("kobe");
        message.setContent("退役啦");
        messageRepository.save(message);

        return  article;
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void updateArticle(ArticleVO article) {
        Article articlePO = dozerMapper.map(article,Article.class);
        articleRepository.save(articlePO);
    }

    @Override
    public ArticleVO getArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        //把读者查出来
        ArticleVO articleVO = dozerMapper.map(article.get(),ArticleVO.class);
        //articleVO.setReader();
        return articleVO;
    }

    @Override
    public List<ArticleVO> getAll() {
        List<Article> articleLis = articleRepository.findAll();

        return DozerUtils.mapList(articleLis,ArticleVO.class);

    }
}