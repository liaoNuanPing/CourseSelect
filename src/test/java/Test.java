import dao.mapper.AdminInfoMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-dao.xml"})
public class Test {

    @Autowired
    AdminInfoMapper adminInfoMapper;

    @org.junit.Test
    public void testCRUD(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
        System.out.println(adminInfoMapper);
    }

}
