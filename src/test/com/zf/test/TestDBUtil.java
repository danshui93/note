package com.zf.test;

import org.junit.Test;

import com.zf.util.DBUtil;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
public class TestDBUtil {
	@Test
	public void testGetConn(){
		
		assertNotNull(DBUtil.getConn());
	}
}
