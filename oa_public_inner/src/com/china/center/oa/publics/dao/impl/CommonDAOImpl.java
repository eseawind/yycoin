package com.china.center.oa.publics.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.jdbc.inter.JdbcOperation;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.statics.PublicStatic;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;


/**
 * CommonDAOImpl
 * 
 * @author ZHUZHU
 * @version 2012-4-15
 * @see CommonDAOImpl
 * @since 3.0
 */
public class CommonDAOImpl implements CommonDAO
{
    private final Log _logger = LogFactory.getLog(getClass());

    private JdbcOperation jdbcOperation = null;

    private static int squenceBegin = 0;

    private static int squenceEnd = 0;

    public int getSquence()
    {
        synchronized (PublicStatic.getLOCK())
        {
            if (squenceBegin != 0 && squenceBegin < squenceEnd)
            {
                squenceBegin = squenceBegin + 1;

                return squenceBegin;
            }

            Connection con = null;

            PreparedStatement ps = null;

            try
            {
                con = jdbcOperation.getDataSource().getConnection();

                con.setAutoCommit(false);

                String sql = "select max(id) from T_CENTER_SEQUENCE";

                ps = con.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                rs.next();

                int tem = rs.getInt(1);

                _logger.info("apply new current db sequence:" + tem);

                rs.close();

                ps.close();

                int kk = tem;

                if (tem > (Integer.MAX_VALUE - 101000))
                {
                    kk = 0;
                }

                squenceBegin = kk + 1;

                _logger.info("apply new squenceBegin:" + squenceBegin);

                squenceEnd = kk + 1000;

                _logger.info("apply new squenceEnd:" + squenceEnd);

                // kk+1 to kk+1000
                sql = "update T_CENTER_SEQUENCE set id = ? where id = ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, squenceEnd);

                ps.setInt(2, tem);

                ps.execute();

                con.commit();

                _logger.info("apply new squence(in db) success:" + squenceEnd);
            }
            catch (Throwable e)
            {
                _logger.error(e, e);

                return (int)SequenceTools.getCurrentSequence();
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
                        _logger.error(e, e);
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
                        _logger.error(e, e);
                    }
                }
            }

            // fix bug
            return squenceBegin;
        }
    }

    public synchronized String getSquenceString()
    {
        return String.valueOf(this.getSquence());
    }

    public synchronized String getSquenceString20()
    {
        String formatString20 = StringTools.formatString20(String.valueOf(this.getSquence()));

        return formatString20;
    }

    public String getSquenceString20(String pfix)
    {
        String formatString20 = StringTools.formatString20(pfix, String.valueOf(this.getSquence()));

        return formatString20;
    }

    public JdbcOperation getJdbcOperation()
    {
        return jdbcOperation;
    }

    public void setJdbcOperation(JdbcOperation jdbcOperation)
    {
        this.jdbcOperation = jdbcOperation;
    }

    public void updatePublicLock()
    {
        String sql = "update T_CENTER_LOCK set name = 'PUBLICLOCK' where ID = 0";

        jdbcOperation.update(sql);

    }

    @Override
    public void updatePublicLock1() {
        String sql = "update T_CENTER_LOCK set name = 'PUBLICLOCK1' where ID = 1";

        jdbcOperation.update(sql);
        
    }

}
