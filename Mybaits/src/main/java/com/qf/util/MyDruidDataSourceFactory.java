package com.qf.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/5
 * 换成 druid 连接池
 * 要自己构建一个 druid 的工厂
 **/

/**
 * 连接池工厂
 * 将这个工厂安装在 mybaits 的系统中
 * 需要连接池时候来找 druid
 * 在 mybaits 中找到 dataSource 将 type 改成这个连接池即可
 */
public class MyDruidDataSourceFactory extends PooledDataSourceFactory {

    public MyDruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();  //替换数据源
    }

}
