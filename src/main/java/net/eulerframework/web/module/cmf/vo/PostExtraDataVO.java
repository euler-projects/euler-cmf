/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2017 cFrost.sun(孙宾, SUN BIN) 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * For more information, please visit the following website
 * 
 * https://eulerproject.io
 * https://github.com/euler-form/web-form
 * https://cfrost.net
 */
package net.eulerframework.web.module.cmf.vo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.eulerframework.common.util.StringUtils;
import net.eulerframework.web.module.cmf.entity.Post;

/**
 * @author cFrost
 *
 */
public class PostExtraDataVO extends Post {
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public PostExtraDataVO(Post post) {
        BeanUtils.copyProperties(post, this);
    }

    public Map<String, Object> getExtra() throws JsonParseException, JsonMappingException, IOException {
        if(!StringUtils.hasText(this.getExtraData())) {
            return null;
        }
        
        JavaType extraType = this.objectMapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, Object.class);
        return this.objectMapper.readValue(this.getExtraData(), extraType);
    }
}
