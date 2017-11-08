package co.kr.jongin.blog.blog;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.kr.jongin.blog.common.MyAppSqlConfig;

public class BlogMapper {

	private final static String NS = "co.kr.jongin.blog.blog.BlogMapper.";
	
	private static SqlSession session;

	public BlogMapper () {
		session = MyAppSqlConfig.getSqlSessionInstance();
	}
	
	public void blogInsert(BlogDomain blog) throws Exception {
		session.insert(NS + "blogInsert", blog);
	}
	
	public int chkMyBlog(int member_no) throws Exception {
		return session.selectOne(NS + "chkMyBlog", member_no);
	}
	
	public List<BlogDomain> blogList(BlogDomain blog) throws Exception {
		return session.selectList(NS + "blogList", blog);
	}
	
}