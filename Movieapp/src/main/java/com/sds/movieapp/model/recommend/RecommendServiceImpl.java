package com.sds.movieapp.model.recommend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.domain.MovieDoc;
import com.sds.movieapp.model.comments.CommentsDocDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	private CommentsDocDAO commentsDocDAO;
	
	@Autowired
	private MovieDocDAO movieDocDAO;
	
	private double minScore=1.0;
	
	public List getList(int member_idx) {
		
		/*-----------------------------------------
		 1) 영화평 리스트를 Mahout  전용  List로 전환
		      -  List  는 Preference로 이루어진 List 로 전환 
		      -  CommentsDoc  는  Preference 로 전환  
		      
		 2) 영화에 대한 메타정보도 수집하자 
		-----------------------------------------*/
		List<CommentsDoc> commentsList = commentsDocDAO.selectAllByMemember(member_idx);
		Map<Long, MovieDoc> metadataMap = new HashMap();//해당 영화평에 대한 영화들을 모아놓을 맵
		
		List<Preference> preferences = new ArrayList();
		
		for(int i=0; i<commentsList.size();i++) {
			CommentsDoc doc = commentsList.get(i);
			GenericPreference preference = new GenericPreference((long)doc.getMember_idx(), doc.getMovie_idx(), doc.getScore()); 
			preferences.add(preference);
			
			MovieDoc movieDoc = movieDocDAO.select( doc.getMovie_idx() );
			metadataMap.put((long)movieDoc.getMovie_idx(), movieDoc);
			
		}
		
		/*-----------------------------------------
		 영화평 리스트에서, 긍정 평가한 리스트 추려내기
		-----------------------------------------*/
		preferences.stream().forEach(m -> log.debug("긍정평가 전 영화명 "+metadataMap.get(m.getItemID())));
		
		List<MovieDoc> likedMovies =preferences.stream()
			.filter(p -> p.getValue() >= minScore )  //추려내는게 목적인 메서드 따라서 filter  수행 후 리스트는 줄어들 수 있다.
			.map(p -> metadataMap.get(p.getItemID()))
			.collect(Collectors.toList()); //최종 메서드
		
		//긍정 평가 리스트 출력 테스트
		likedMovies.stream().forEach(m -> log.debug("긍정 평가한 영화명은 "+m.getMovieNm()));
		
	
		/*-----------------------------------------
		 홈페이지의 모든 영화(유저가 클릭한 영화들...)와 긍정 평가한 영화와의 유사도를 체크하여 
		 유사도가 높은 상위 n개를 추천영화로 등록 
		-----------------------------------------*/
		List<MovieDoc> movieList = movieDocDAO.selectAll(null);
		Map<Long, MovieDoc> candiMap = new HashMap();//리스트를 맵으로 전환 목적
		movieList.stream().forEach(m-> candiMap.put((long)m.getMovie_idx(), m)); //맵에 옮겨 담기 완료
		
		//모든 영화에 대한 계산결과를 담아놓ㅇ르 Map - 영화 idx
		Map<Long, Double> calculatedMap = new HashMap();
		
		for(MovieDoc movieDoc : likedMovies) { //긍정 평가한 영화들만큼...
			for(Map.Entry<Long, MovieDoc> entry : candiMap.entrySet()) { //홈페이지의 모든 영화들만큼...
				//유사도 측정.. 상위 n개만 최종적으로 추려냄...
				//감독,배우, 국가, 장르..
				MovieDoc candi = entry.getValue();
				
				double score = calculate(movieDoc, candi);//유사도 메서드 호출()
				calculatedMap.put(entry.getKey(),score);
				
			}
		}
		log.debug("계산 결과 사이즈 " +calculatedMap.size());
		for(Map.Entry<Long, Double>entry :calculatedMap.entrySet()) {
			MovieDoc movieDoc = metadataMap.get(entry.getKey());
			log.debug(movieDoc.getMovieNm()+" 의 유사도는 "+entry.getValue());
		}

		return null;
	}
	
	//유사도 계산 (넘겨받은 두 영화 사이의 유사도 측정)
	public double calculate(MovieDoc m1, MovieDoc m2) {
		double score = 0.0;
		
		//감독 유사도 측정
		String[] directors1 = m1.getDirectors();
		String[] directors2 = m2.getDirectors();
		List<String> listDirectors1 = Arrays.asList(directors1); // 배열을 리스트로 자동 전환
		List<String> listDirectors2 = Arrays.asList(directors2); // 배열을 리스트로 자동 전환
		
		for(String director  : listDirectors1) {
			if(listDirectors2.contains(director)) {
				score += 0.5; //몇점을 부여할지는 개발자가 알아서 조절
				break;
			}
		}
		
		
		//배우 유사도 측정
		String[] actors1 = m1.getActors();
		String[] actors2 = m2.getActors();
		List<String> listActors1 = Arrays.asList(actors1); // 배열을 리스트로 자동 전환
		List<String> listActors2 = Arrays.asList(actors2); // 배열을 리스트로 자동 전환
		
		for(String actor  : listActors1) {
			if(listActors2.contains(actor)) {
				score += 0.5;
				break;
			}
		}
		
		
		//장르 유사도 측정
		String[] genres1 = m1.getGenres();
		String[] genres2 = m2.getGenres();
		List<String> listGenres1 = Arrays.asList(genres1); // 배열을 리스트로 자동 전환
		List<String> listGenres2 = Arrays.asList(genres2); // 배열을 리스트로 자동 전환
		
		for(String genre  : listGenres1) {
			if(listGenres2.contains(genre)) {
				score += 0.6;
				break;
			}
		}
		
		
		//국가 유사도 측정
		String[] nations1 = m1.getNations();
		String[] nations2 = m2.getNations();
		List<String> listNations1 = Arrays.asList(nations1); // 배열을 리스트로 자동 전환
		List<String> listNations2 = Arrays.asList(nations2); // 배열을 리스트로 자동 전환
		
		for(String nation  : listNations1) {
			if(listNations2.contains(nation)) {
				score += 0.3;
				break;
			}
		}
		
		return score;
	}
}






