package com.sds.movieapp.model.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sds.movieapp.domain.Director;
import com.sds.movieapp.domain.Movie;
import com.sds.movieapp.domain.MovieType;
import com.sds.movieapp.domain.Nation;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.MovieAPIServiceImplService;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.MovieInfoResult;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.OpenAPIFault;

//api 호출하여 각종 정보를 가져오는 전담 서비스 (영화, 나라정보, 유형, 영화조회..)
@Service
public class MovieApiService {

//	@Value("${movie.api.key}") //application.properties 파일의 key값 참조
	@Autowired
	private String key;
		
	@Autowired
	private KobisOpenAPIRestService kobisOpenAPIRestService;	
	
	
	/*---------------------------------------------------------
	영화목록 조회
	---------------------------------------------------------*/
	public List getMovieList(Movie movie) {
	    String movieCdResponse=null;	
	    List<Movie> movieList=null;
	    
		try {
		    movieCdResponse = kobisOpenAPIRestService.getMovieList(true, movie.getCurPage(), movie.getItemPerPage(), movie.getMovieNm()
		    		, movie.getDirectorNm(), movie.getOpenStartDt(), movie.getOpenEndDt(), movie.getPrdtStartYear()
		    		, movie.getPrdtEndYear(), movie.getRepNationCd(), movie.getMovieTypeCdArr());
		    
		    System.out.println(movieCdResponse);
		    
		    //json 문자열을 자바 객체로 변환 
		    JSONParser parser = new JSONParser();
		    JSONObject json =(JSONObject)parser.parse(movieCdResponse); //가장 바깥쪽 JSON 반환
		    json = (JSONObject)json.get("movieListResult");//그 다음 안쪽 JSON 반환
		    JSONArray array = (JSONArray)json.get("movieList"); //영화 배열 반환
		    System.out.println("영화 목록의 수는 "+array.size());
		    
		    //JSON simple은 파싱할때는 편하다..하지만, 파싱 결과를 자바의 List 등 컬렉션으로 바꾸는 작업은 GSON이 능하다
		    // json 를 자바로, 자바를 json으로 변환할때 능하다 
		    Gson gson=new Gson();
		    Type movieArray   = new TypeToken<List<Movie>>() {}.getType();
		    movieList = gson.fromJson(array.toJSONString(), movieArray);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movieList;
	}
	
	/*---------------------------------------------------------
	국가정보
	---------------------------------------------------------*/
	public List getNationList() {
		
		List<Nation> nationList=null;
		
		String nationCdResponse = null;
		
		try {
			nationCdResponse = kobisOpenAPIRestService.getComCodeList(true,"2204");
			//JSON 스트링에 불과한 데이터를 JSON으로 파싱한 후, 자바 객체로 다시 변환..
			JSONParser parser = new JSONParser(); //simple 파서 생성
			JSONObject json = (JSONObject)parser.parse(nationCdResponse);
			JSONArray array = (JSONArray)json.get("codes");//국가 목록 배열 반환
			
			System.out.println(array);
			
			Gson gson = new Gson();
			Type nationType = new TypeToken<List<Nation>>() {}.getType();
			nationList = gson.fromJson(array.toJSONString(), nationType);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return nationList;
	}
	
	/*---------------------------------------------------------
	영화유형
	---------------------------------------------------------*/
	public List getTypeList() {
		List<MovieType> movieTypeList=null;
		String movieTypeCdResponse = null;
		try {
			movieTypeCdResponse = kobisOpenAPIRestService.getComCodeList(true,"2201");
			
			//String형을 JSON으로 파싱하기 (json simple)
			JSONParser parser = new JSONParser(); //simple 파서 생성
			JSONObject json = (JSONObject)parser.parse(movieTypeCdResponse);
			JSONArray array = (JSONArray)json.get("codes");
			
			System.out.println(array);
			
			Gson gson = new Gson();
			Type movieType = new TypeToken<List<MovieType>>() {}.getType();
			movieTypeList = gson.fromJson(array.toJSONString(), movieType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieTypeList;
	}
	
	/*---------------------------------------------------------
	영화 1건 조회
	오픈 API 에서 가져온 영화 정보를 Movie DTO 변환하여 반환하자(즉 영화정보를 더 채워서 반환하자)
	---------------------------------------------------------*/
	public Movie getMovie(Movie movie) { // DAO가 반환한 List 안에 부족한 영화정보를 가진 Movie DTO(movieCd,url)
		MovieInfoResult movieInfoResult = null;
		
		try {
			movieInfoResult = new MovieAPIServiceImplService().getMovieAPIServiceImplPort().searchMovieInfo(key, movie.getMovieCd());
			
			
			movie.setMovieNm(movieInfoResult.getMovieInfo().getMovieNm());//영화이름
			movie.setPrdtYear(movieInfoResult.getMovieInfo().getPrdtYear());//제작일
			movie.setOpenDt(movieInfoResult.getMovieInfo().getOpenDt());//개봉일
			
			List<Director> directorList=new ArrayList<Director>();//감독을 채워넣을 List
			for(int i=0;i<movieInfoResult.getMovieInfo().getDirectors().getDirector().size();i++) {
				String dname=movieInfoResult.getMovieInfo().getDirectors().getDirector().get(i).getPeopleNm();
				Director director = new Director();//감독 객체 생성 
				director.setPeopleNm(dname);
				directorList.add(director);
			}
			movie.setDirectors(directorList);//Movie DTO에 감독 List을 대입 
			
		} catch (OpenAPIFault e) {
			e.printStackTrace();
		}
		
		//영화 1건 조회하기 
		return movie;
	}
}








