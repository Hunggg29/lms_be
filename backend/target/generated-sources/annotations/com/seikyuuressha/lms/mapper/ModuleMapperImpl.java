package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.ModuleResponse;
import com.seikyuuressha.lms.entity.Module;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:07+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ModuleMapperImpl implements ModuleMapper {

    @Override
    public ModuleResponse toModuleResponse(Module module) {
        if ( module == null ) {
            return null;
        }

        ModuleResponse.ModuleResponseBuilder moduleResponse = ModuleResponse.builder();

        moduleResponse.order( module.getSortOrder() );
        moduleResponse.moduleId( module.getModuleId() );
        moduleResponse.title( module.getTitle() );

        return moduleResponse.build();
    }

    @Override
    public ModuleResponse toModuleResponseWithLessons(Module module) {
        if ( module == null ) {
            return null;
        }

        ModuleResponse.ModuleResponseBuilder moduleResponse = ModuleResponse.builder();

        moduleResponse.order( module.getSortOrder() );
        moduleResponse.lessons( mapLessons( module.getLessons() ) );
        moduleResponse.moduleId( module.getModuleId() );
        moduleResponse.title( module.getTitle() );

        return moduleResponse.build();
    }
}
