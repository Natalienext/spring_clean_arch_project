package musicsearchportal.domain.usecase.post;

import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.usecase.CreatePostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

@Service
public class CreatePostUseCaseImpl implements CreatePostUseCase {

    @Autowired
    private static IdGenerator idGenerator;

    @Override
    public CreatePostResult create(CreatePostParam params) {
        return new CreatePostResult(idGenerator.generateId().toString());
    }
}
