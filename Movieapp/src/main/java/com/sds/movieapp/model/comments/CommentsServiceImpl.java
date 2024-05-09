package com.sds.movieapp.model.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.domain.MovieDoc;
import com.sds.movieapp.domain.SentimentDic;
import com.sds.movieapp.model.recommend.SentimentDicDAO;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.util.common.model.Pair;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private Komoran komoran;
	
	@Autowired
	private SentimentDicDAO sentimentDicDAO;
	
	@Autowired
	private CommentsDocDAO commentsDocDAO;
	
	
	public void registComments(CommentsDoc commentsDoc, MovieDoc movieDoc) {
		String text = commentsDoc.getContent().replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
		
		//코모란 에게 영화평을 형태소 단위로 나누도록 시키자
		KomoranResult result = komoran.analyze(text);
		
		List< Pair<String, String> > resultList = result.getList();
		
		//나누어진 형태소 수 만큼 반복... 감성 사전 조회도 함께 진행하자
		float score=0;
		for( Pair<String, String> pair : resultList) {
			String word = pair.getFirst(); //형태소 
			
			log.debug(word+", "+pair.getSecond());    
			
			String formmattedWord  = word +"/"+pair.getSecond(); // 재미/NNG
			
			SentimentDic sentimentDic = sentimentDicDAO.select(formmattedWord);
			
			
			if(sentimentDic !=null) {//사전에 있는 형태소라면...
				log.debug(word+"의 사전검색 결과 POS="+sentimentDic.getPOS()+", NEG="+sentimentDic.getNEG());
				score += sentimentDic.getPOS() - sentimentDic.getNEG(); 
			}else {
				log.debug("사전에 없음");
			}
		}
		log.debug("당신이 입력한 영화평 내용을 분석한 결과 "+score);
		
		commentsDoc.setScore(score);
		
		commentsDocDAO.insert(commentsDoc); //누가 member_idx, 어떤내용? content, 어떤 영화를? movie_idx
	}

}




