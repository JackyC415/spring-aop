package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired TweetStatsServiceImpl stats;
	
	@AfterReturning(pointcut="execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))", returning = "retVal")
	public void validatedTweet(JoinPoint joinPoint, Object retVal){
		String user = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		stats.addTweets(user, message);
		stats.addDistinctTweets(user, message);
		System.out.printf("After validating the executuion of the method %s\n", joinPoint.getSignature().getName());
	}
	
	@AfterReturning(pointcut="execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))", returning = "retVal")
	public void validatedFollower(JoinPoint joinPoint, Object retVal){
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		stats.addFollowers(follower, followee);
		System.out.printf("After validating the executuion of the method %s\n", joinPoint.getSignature().getName());
	}
	
}
