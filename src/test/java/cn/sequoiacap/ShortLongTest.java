package cn.sequoiacap;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cn.sequoiacap.utils.NumericConvertUtils;
import cn.sequoiacap.utils.SnowFlake;

@SpringBootTest

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ShortLongTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@org.junit.jupiter.api.Test
	public void test() {
		SnowFlake snowFlake = new SnowFlake(2, 3);

		for (int i = 0; i < (1 << 12); i++) {
			System.out.println(snowFlake.nextId());
		}
	}

	@Test
	public void toShortTest() throws Exception {
		SnowFlake snowFlake = new SnowFlake(0, 0);
		for (int i = 0; i < 10; i++) {
			System.out.println(NumericConvertUtils.toOtherNumberSystem(snowFlake.nextId(), 62));
		}
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/test/toShort")
                .param("url", "http://www.sina.com/xxx?12323;html")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);

	}
	
	@Test
	public void getLongTest() throws Exception{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/test/getLong")
                .param("url", "http://www.sina.com")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);

	}

}
