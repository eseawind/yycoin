package com.china.center.oa.product.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.china.center.jdbc.inter.JdbcOperation;
import com.china.center.oa.product.dao.SequenceDAO;
import com.china.center.tools.StringTools;

public class SequenceDAOImpl implements SequenceDAO
{
	private JdbcOperation jdbcOperation = null;
	
	private static Object lock = new Object();
	
	@Override
	public int getSquence(String head)
	{
		int newId = 0;
		
		synchronized(lock){
			Connection con = null;

            PreparedStatement ps = null;

            try
            {
                con = jdbcOperation.getDataSource().getConnection();

                con.setAutoCommit(false);

                String sql = "select max(id) from T_CENTER_PRODUCTSEQUENCE where head = '" + head + "'";

                ps = con.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                rs.next();

                int tem = rs.getInt(1);

                rs.close();

                ps.close();

                int kk = tem;

                if (tem > 99999)
                {
                    kk = 0;
                }

                newId = kk + 1;

                // kk+1 to kk+1000
                sql = "update T_CENTER_PRODUCTSEQUENCE set id = ? where id = ? and head = ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, newId);

                ps.setInt(2, tem);
                
                ps.setString(3, head);

                ps.execute();

                con.commit();

            }
            catch (Throwable e)
            {
                throw new RuntimeException(e);
            }
            finally
            {
                if (ps != null)
                {
                    try
                    {
                        ps.close();
                    }
                    catch (Throwable e)
                    {
                    }
                }

                if (con != null)
                {
                    try
                    {
                        con.close();
                    }
                    catch (Throwable e)
                    {
                    }
                }
            }
		}
		
		return newId;
	}

	public synchronized String getSquenceString5(String head)
	{
		String formatString5 = StringTools.formatString(String.valueOf(this.getSquence(head)), 5);

        return formatString5;
	}

	/**
	 * @return the jdbcOperation
	 */
	public JdbcOperation getJdbcOperation()
	{
		return jdbcOperation;
	}

	/**
	 * @param jdbcOperation the jdbcOperation to set
	 */
	public void setJdbcOperation(JdbcOperation jdbcOperation)
	{
		this.jdbcOperation = jdbcOperation;
	}
}
