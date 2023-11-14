package edu.ncsu.csc.iTrust2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import edu.ncsu.csc.iTrust2.models.ICDCode;
import edu.ncsu.csc.iTrust2.repositories.ICDCodeRepository;
import java.util.Arrays;

@Component
public class ICDCodeDefault implements CommandLineRunner {

    private final ICDCodeRepository repository;

    public ICDCodeDefault(ICDCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        // 여기에 초기화하고자 하는 ICD 코드를 추가합니다.
        if (repository.count() == 0) { // DB가 비어있을 때만 실행
            repository.saveAll(Arrays.asList(
                new ICDCode("H26.9", "cataracts"),
                new ICDCode("H35.30", "age-related macular degeneration'"),
                new ICDCode("H40.9", "glaucoma"),
                new ICDCode("H53.009", "amblyopia")
            ));
        }
    }

    private ICDCode newICDCode(String code, String description) {
        ICDCode icdCode = new ICDCode();
        icdCode.setCode(code);
        icdCode.setDescription(description);
        return icdCode;
    }
}