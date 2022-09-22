package scheduler.api.rest;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


/**
 * Hateoas 및 SelfDescriptive 정보를 해더에 추가하는 책임 클래스
 */
@Component
public class RestHeaderCreator {

    private static final String self_descriptive_msg_info = "; href=next state";
    private static final String hateoas_msg_info = "<https://exmaple.org/docs/>;  rel=\"api doc\"";


    public HttpHeaders createRestfulHeader(Class<?> controller , String remainUrls ,String httpMethod ){

        HttpHeaders restfulHeader = new HttpHeaders();
        addSelfDescriptiveHeader(restfulHeader, controller,remainUrls,httpMethod);
        addHateoasHeader(restfulHeader);
        return restfulHeader;
    }

    /**
     * Hateoas 정보 해더 생성
     */
    private void addHateoasHeader(HttpHeaders headers){
        headers.add("Link" , hateoas_msg_info);
    }

    /**
     * SelfDescriptive 정보 해더 생성
     */
    private void addSelfDescriptiveHeader(HttpHeaders header ,Class<?> controller , String remainUrls ,String httpMethod){

        StringBuffer link = new StringBuffer(httpMethod);
        link.append(linkTo(controller).slash(remainUrls).toString());
        link.append(self_descriptive_msg_info);

        header.add("Location" , link.toString());
    }


}
