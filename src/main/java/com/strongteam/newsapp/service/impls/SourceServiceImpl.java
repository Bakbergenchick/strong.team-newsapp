package com.strongteam.newsapp.service.impls;

import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.repository.SourceRepository;
import com.strongteam.newsapp.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;

    @Override
    public List<Source> getAllSource() {
        return sourceRepository.findAll();
    }

    @Override
    public Optional<Source> getSourceById(Long id) {
        return sourceRepository.findById(id);
    }

    @Override
    public Source saveOrUpdateSource(Source source) {
        return sourceRepository.save(source);
    }

    @Override
    public void deleteSourceById(Long id) {
        sourceRepository.deleteById(id);
    }
}
