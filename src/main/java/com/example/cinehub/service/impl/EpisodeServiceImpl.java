package com.example.cinehub.service.impl;

import com.example.cinehub.dto.EpisodeDTO;
import com.example.cinehub.entity.Episode;
import com.example.cinehub.entity.Movie;
import com.example.cinehub.exception.ResourceNotFoundException;
import com.example.cinehub.repository.EpisodeRepository;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<EpisodeDTO> getEpisodeByMovieId(Long id) {
        if(!movieRepository.existsById(id))
            throw new ResourceNotFoundException("Không tìm thấy film với ID = " + id);
        List<Episode> episodes = episodeRepository.findByMovieIdOrderByEpisodeNumberAsc(id);
        return episodes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EpisodeDTO addEpisode(Long id, Episode episode) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy fim với ID = " + id));
        episode.setMovie(movie); // Khoa ngoại kết nối tập phim với bo phim
        return convertToDTO(episodeRepository.save(episode));
    }

    @Override
    public EpisodeDTO updateEpisode(Long id, Episode episodeDetails) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy tập fim với ID = " +id));

        episode.setTitle(episodeDetails.getTitle());
        episode.setEpisodeNumber(episodeDetails.getEpisodeNumber());
        episode.setVideoUrl(episodeDetails.getVideoUrl());
        episode.setThumbnailUrl(episodeDetails.getThumbnailUrl());
        episode.setDuration(episodeDetails.getDuration());

        return convertToDTO(episodeRepository.save(episode));
    }

    @Override
    public void deleteEpisode(Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy tập film với ID = " + id));
        episodeRepository.delete(episode);
    }

    private EpisodeDTO convertToDTO(Episode episode) {
        EpisodeDTO episodeDTO = new EpisodeDTO();
        episodeDTO.setId(episode.getId());
        episodeDTO.setTitle(episode.getTitle());
        episodeDTO.setEpisodeNumber(episode.getEpisodeNumber());
        episodeDTO.setVideoUrl(episode.getVideoUrl());
        episodeDTO.setThumbnailUrl(episode.getThumbnailUrl());
        episodeDTO.setDuration(episode.getDuration());
        return episodeDTO;
    }
}
