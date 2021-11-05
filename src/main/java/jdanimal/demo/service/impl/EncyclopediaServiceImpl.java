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
    public boolean saveInDB(EncyclopediaAnimalBinding encyclopediaAnimalBinding) {
        try {
            EncyclopediaAnimalUploadModel mapped = this.modelMapper.map(encyclopediaAnimalBinding, EncyclopediaAnimalUploadModel.class);
            EncyclopediaAnimal mappedToEntity = this.modelMapper.map(mapped, EncyclopediaAnimal.class);
            this.encyclopediaRepository.saveAndFlush(mappedToEntity);
            updateEncyclopedia();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<EncyclopediaAnimalViewModel> findAllAnimalsInEncyclopedia() {
        return this.encyclopediaRepository.getAllAnimals().stream().map(encyclopediaAnimal ->
                this.modelMapper.map(encyclopediaAnimal,EncyclopediaAnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAnimalFromEncyclopedia(String id) {
        try {
            this.encyclopediaRepository.deleteById(id);
            updateEncyclopedia();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<EncyclopediaAnimalViewModel> animalsFilterByType(String type) {
        return this.encyclopediaRepository.getAllByTypeOfAnimal(AnimalType.valueOf(type)).stream().map(encyclopediaAnimal ->
                this.modelMapper.map(encyclopediaAnimal, EncyclopediaAnimalViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean updateEncyclopedia() {
        try {
            this.encyclopediaRepository.findAll();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
