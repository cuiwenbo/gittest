package com.manong.mall.service;

import com.manong.mall.action.Page;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl {
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat df14 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat dfdate = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat ndf2 = new DecimalFormat("#,##0.00");
    public static DecimalFormat ndf3 = new DecimalFormat("0.00");
    public static DecimalFormat ndf4 = new DecimalFormat("0.0000");
    public static SimpleDateFormat df4 = new SimpleDateFormat("yyyy.MM.dd");
    public static DecimalFormat ndf_8 = new DecimalFormat("00000000");

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
    public int listObj(Map param, Page page, String sql_count, String sql_list) {
        try {
            int count = (Integer)get(sql_count, param);
            page.setTotal(count);
            param.put("firstResult", page.getFirst());
            param.put("maxResult",page.getPageSize());
            List list = list(sql_list, param);
            if(list == null) list = new ArrayList();
            page.setElements(list);
        } catch (Exception e) {
            e.printStackTrace();
            return FAILED;
        }
        return SUCCESS;
    }
}
