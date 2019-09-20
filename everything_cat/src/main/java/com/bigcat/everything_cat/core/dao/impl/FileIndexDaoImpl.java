package com.bigcat.everything_cat.core.dao.impl;

import com.bigcat.everything_cat.core.dao.FileIndexDao;
import com.bigcat.everything_cat.core.model.Condition;
import com.bigcat.everything_cat.core.model.FileType;
import com.bigcat.everything_cat.core.model.Thing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:
 */
public class FileIndexDaoImpl implements FileIndexDao {


    //DataSource.getConnection 通过数据源工厂获取DataSource实例化对象

    private final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Thing thing) {
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            //1.获取链接
            connection=this.dataSource.getConnection();
            //2.sql语句
            String sql="insert into thing (name,path,depth,file_type) values(?,?,?,?)";
            //3.准备命令
            statement=connection.prepareStatement(sql);

            statement.setString(1,thing.getName());
            statement.setString(2,thing.getPath());
            statement.setInt(3,thing.getDepth());
            statement.setString(4,thing.getFileType().name());

            //5.执行命令
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            releaseResource(null,statement,connection);
        }
    }

    @Override
    public void delete(Thing thing) {
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            //1.获取链接
            connection=this.dataSource.getConnection();
            //2.sql语句__难点：删除时应该递归的删除
            //解决：like path%
            String sql="delete from thing where path like '"+thing.getPath()+"%'";

            //3.准备命令
            statement=connection.prepareStatement(sql);
            //4.设置参数
            //5.执行命令
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            releaseResource(null,statement,connection);
        }
    }

    @Override
    public List<Thing> query(Condition condition) {
        List<Thing> things = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select name,path,depth,file_type from thing ");
            sb.append("where ");
            //模糊匹配 后模糊
            //search <name> [file_type]
            sb.append("name like '").append(condition.getName()).append("%'");

            if (condition.getFileType() != null) {
                FileType fileType = FileType.lookupByName(condition.getFileType());
                sb.append("and file_type='" + fileType.name()).append("'");
            }
            sb.append(" order by depth ")
                    .append(condition.getOrderByDepthAsc() ? "asc" : "desc");
            sb.append(" limit ").append(condition.getLimit());

            statement = connection.prepareStatement(sb.toString());
            //预编译命令中SQL的占位符赋值
            resultSet = statement.executeQuery();
            //处理结果
            while (resultSet.next()) {

                Thing thing = new Thing();
                thing.setName(resultSet.getString("name"));
                thing.setPath(resultSet.getString("path"));
                thing.setDepth(resultSet.getInt("depth"));
                String fileType = resultSet.getString("file_type");
                thing.setFileType(FileType.lookupByName(fileType));
                things.add(thing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseResource(resultSet,statement,connection);
        }
        return things;
//        Connection connection=null;
//        PreparedStatement statement=null;
//        ResultSet resultSet=null;
//        List<Thing> things=new ArrayList<>();
//        try{
//            //1.获取链接
//            connection=dataSource.getConnection();
//            //2.sql语句
//            //String sql="select name,path,depath,file_type from file_index";
//
//            //name——后模糊匹配查询----%
//            //filetype——准确查询----=
//            //查询结果按文件深度升序排列
//            // StringBuilder比Stringbuffer效率高，异步操作
//            StringBuilder sqlBuilder=new StringBuilder();
//            sqlBuilder.append(" select name,path,depth,file_type from thing ");
//
//            sqlBuilder.append("where ")
//                    .append("name like '")
//                    .append(condition.getName())
//                    .append("%' ");
//            if (condition.getFileType()!=null){
//                FileType fileType=FileType.lookupByName(condition.getFileType());
//                //FileType fileType=FileType.lookupByExtend(condition.getFiletype());
//                sqlBuilder.append("and file_type='"+fileType.name())
//                        .append("'");
//            }
//
////            //limit和orderby的实现,先排序
//            if (condition.getOrderByAsc()!=null){
//                sqlBuilder.append(" order by depth ")
//                        .append(condition.getOrderByAsc() ? "asc" : "desc");
//            }
//
//            if (condition.getLimit()!=null){
//                sqlBuilder.append(" limit ")
//                        .append(condition.getLimit());
//            }
//
//            //打印出sql查询语句
//            System.out.println(sqlBuilder.toString());
//            //3.准备命令
//            statement=connection.prepareStatement(sqlBuilder.toString());
//            //5.执行命令
//            resultSet=statement.executeQuery();
//            //6.处理结果
//            while (resultSet.next()){
//                Thing thing=new Thing();
//                thing.setName(resultSet.getString("name"));
//                thing.setPath(resultSet.getString("path"));
//                thing.setDepth(resultSet.getInt("depth"));
//                String fileType=resultSet.getString("file_type");
//                thing.setFileType(FileType.lookupByName(fileType));
//                things.add(thing);
//
//                for (Thing thing1:things) {
//                    System.out.println(thing1);
//                }
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//
//        }finally {
//            releaseResource(resultSet,statement,connection);
//        }
//
//        return things;
    }


        //关闭
        private void releaseResource (ResultSet resultSet, PreparedStatement statement, Connection connection){
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    //测试代码
//    public static void main(String[] args) {
//        DataSource dataSource= (DataSource) DataSourceFactory.getInstance();
//        FileIndexDao dao=new FileIndexDaoImpl(dataSource);
//
//        Condition condition=new Condition();
//        condition.setName("文件1.pdf");
//
//        //condition.setFiletype("doc");
//        List<Thing> things=dao.query(condition);
//        for (Thing thing:things) {
//            System.out.println(thing);
//        }
//
//    }



