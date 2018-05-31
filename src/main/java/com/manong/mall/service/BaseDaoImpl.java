package com.manong.mall.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 *
 */
public class BaseDaoImpl {
    @Autowired
    public SqlSessionTemplate sqlSession;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    public SqlSession getSession(boolean autocommit) {
        return sqlSessionFactory.openSession(autocommit);
    }

    public List list(String sql, Object param) {
        try {
            return  sqlSession.selectList(sql, param);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public <T> T get(String sql, Object param) {

        try {
            return sqlSession.selectOne(sql, param);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int add(String sql, Object obj) {
        try {
            sqlSession.insert(sql, obj);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILED;
        }
    }
    public int update(String sql, Object obj) {

        try {
            int i = sqlSession.update(sql, obj);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int delete(String sql, Object obj) {
        try {
            sqlSession.delete(sql, obj);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILED;
        }
    }

}
