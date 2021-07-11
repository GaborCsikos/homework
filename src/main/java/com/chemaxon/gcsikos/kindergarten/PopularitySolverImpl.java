package com.chemaxon.gcsikos.kindergarten;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PopularitySolverImpl implements PopularitySolver{

    @Override
    public String solve(InputStream inputStream) {

        StringBuilder response = new StringBuilder("Popular by vote number:");
        List<String> lines = readStreamToLines(inputStream);

        if(!CollectionUtils.isEmpty(lines)) {
            Map<String, Set<String>> processed = new HashMap<>();
            lines.stream().forEach(line -> {
                log.info("Processing line:"+line);
                processRawLine(processed, line);
            });
            log.info("Votes"+processed);
            Map<String, Integer> processedVotes = processVotesByPopularity(processed);
            log.info("Popularity:"+ processedVotes);
            countBiggestPopularity(response, processedVotes);

        } else{
            log.error("empty lines");
        }

        /*String responseToReturn = response.toString();
        if(responseToReturn.charAt(responseToReturn.length()-1) == ',') {
            responseToReturn  = responseToReturn.substring(0, responseToReturn.length() - 1);
        }*/
        return response.toString();
    }

    private void processRawLine(Map<String, Set<String>> processed, String line) {
        String[] nameOfChildArray  = line.split(":");
        if(nameOfChildArray.length == 2) {
            String nameOfChild = nameOfChildArray[0];
            String likesRawList = nameOfChildArray[1];
            String[] splittedLikes = likesRawList.split(",");
            if(splittedLikes.length>0) {
                for(String like : splittedLikes) {
                    addChildren(processed, nameOfChild, like);
                }

            }
        }else {
            log.error("Invalid format");
        }
    }

    private void addChildren(Map<String, Set<String>> processed, String nameOfChild, String like) {
        if(!nameOfChild.equals(like)) {
           if(processed.containsKey(nameOfChild)) {
               Set<String> original = processed.get(nameOfChild);
               original.add(like);
               processed.put(nameOfChild, original);
           } else {
               Set<String> toAdd  = new HashSet<>();
               toAdd.add(like);
               processed.put(nameOfChild, toAdd);
           }
        }
    }

    private void countBiggestPopularity(StringBuilder response, Map<String, Integer> processedVotes) {
        OptionalInt maxLikesOptional= processedVotes.values().stream().mapToInt(x->x).max();
        if(maxLikesOptional.isPresent()){
            int maxVote = maxLikesOptional.getAsInt();
            List<String> filteredNames = processedVotes.entrySet().stream()
                    .filter( x -> x.getValue() == maxVote)
                    .map(x-> x.getKey()).collect(Collectors.toList());
            response.append(maxVote).append(" name(s):" +filteredNames);
        }else{
            log.error("could not determine maximum");
        }
    }

    private Map<String, Integer> processVotesByPopularity(Map<String, Set<String>> processed) {
        Map<String, Integer> processedVotes = new HashMap<>();
        // check what is the biggest
        processed.entrySet().forEach( entry -> {
            entry.getValue().forEach(personWhoGotTheLike -> {
                processedVotes.merge(personWhoGotTheLike,1 ,(prev, current) -> prev+1);
            });
        });
        return processedVotes;
    }

    private List<String> readStreamToLines(InputStream inputStream) {
        return new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.toList());
    }
}
