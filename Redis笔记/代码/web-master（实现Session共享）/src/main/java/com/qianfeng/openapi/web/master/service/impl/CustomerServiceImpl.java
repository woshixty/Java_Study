package com.qianfeng.openapi.web.master.service.impl;


//
//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖镇楼                  BUG辟易  
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.mapper.CustomerMapper;
import com.qianfeng.openapi.web.master.pojo.Customer;
import com.qianfeng.openapi.web.master.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jackiechan on 2020-04-05 04:34
 *
 * @Author jackiechan
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void addCustomer(Customer customer) throws Exception {
        customerMapper.insertCustomer(customer);
    }

    @Override
    public PageInfo<Customer> getCustomerList(Customer criteria, int page, int limit) {
        PageHelper.startPage(page, limit);
        return new PageInfo<>(customerMapper.queryCustomer(criteria));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        for (int id : ids) {
            Customer customer = customerMapper.getCustomerById(id);
            if (customer != null) {
                customer.setState(0);
                customerMapper.updateCustomer(customer);
            }
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerMapper.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerMapper.getAllCustomer();
    }

    @Override
    public void addMoney(Integer money, int id) {
        Customer customer = customerMapper.getCustomerById(id);
        if (customer != null) {
            customer.setMoney(customer.getMoney() + money);
            customerMapper.updateCustomer(customer);
        }
    }
}
