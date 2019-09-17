package com.e2e.constants;

/**
 * Any route details should be fetch by this constants. By convention, lets have payload list constants declaration and route declarations 
 * to be the same to make it simple.
 * @author Karthick Arunachalam
 *
 */
public class RouteList {
	public static final String GETUSERS = "//routes/json-server/getUser";
	public static final String GET_USER_BY_ID = "//routes/json-server/getUserById";
	public static final String UPDATE_TITLE_BY_ID = "//routes/json-server/updateTitleById";
	public static final String ADD_COMMENT = "//routes/json-server/addCommentToPost";
	public static final String GET_COMMENTS_BY_POSTID = "//routes/json-server/getCommentsByPost";
	public static final String GET_ALL_POSTS = "//routes/json-server/getAllPosts";
	public static final String DELETE_POST_BY_ID = "//routes/json-server/deletePostById";
	public static final String GET_COMMENT_BY_ID = "//routes/json-server/getCommentById";
	public static final String UPDATE_EMAIL_BY_ID = "//routes/json-server/updateEmailById";
}
