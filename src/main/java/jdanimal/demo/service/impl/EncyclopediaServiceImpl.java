package jdanimal.demo.service.impl;

import jdanimal.demo.web.binding.EncyclopediaAnimalBinding;
import jdanimal.demo.data.EncyclopediaAnimal;
import jdanimal.demo.data.enums.AnimalType;
import jdanimal.demo.repository.EncyclopediaRepository;
import jdanimal.demo.service.EncyclopediaService;
import jdanimal.demo.service.models.EncyclopediaAnimalUploadModel;
import jdanimal.demo.service.views.EncyclopediaAnimalViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EncyclopediaServiceImpl implements EncyclopediaService {

    private final EncyclopediaRepository encyclopediaRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveInDB(EncyclopediaAnimalBinding encyclopediaAnimalBinding) {
        EncyclopediaAnimalUploadModel mapped = this.modelMapper.map(encyclopediaAnimalBinding, EncyclopediaAnimalUploadModel.class);
        EncyclopediaAnimal mappedToEntity = this.modelMapper.map(mapped, EncyclopediaAnimal.class);
        this.encyclopediaRepository.saveAndFlush(mappedToEntity);
    }

    @Override
    public List<EncyclopediaAnimalViewModel> findAllAnimalsInEncyclopedia() {
        return this.encyclopediaRepository.findAll().stream().map(encyclopediaAnimal ->
                this.modelMapper.map(encyclopediaAnimal,EncyclopediaAnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAnimalFromEncyclopedia(String id) {
        this.encyclopediaRepository.deleteById(id);
    }

    @Override
    public List<EncyclopediaAnimalViewModel> animalsFilterbyName(String type) {
        AnimalType animalType = AnimalType.valueOf(type);
        return this.encyclopediaRepository.getAllByTypeOfAnimal(animalType).stream().map(encyclopediaAnimal ->
                this.modelMapper.map(encyclopediaAnimal,EncyclopediaAnimalViewModel.class)).collect(Collectors.toList());
    }

}
