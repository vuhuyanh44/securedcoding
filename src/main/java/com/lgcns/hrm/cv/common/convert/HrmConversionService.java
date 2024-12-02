package com.lgcns.hrm.cv.common.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

public class HrmConversionService extends ApplicationConversionService {
    @Nullable
    private static volatile HrmConversionService SHARED_INSTANCE;

    public HrmConversionService() {
        this(null);
    }

    public HrmConversionService(@Nullable StringValueResolver embeddedValueResolver) {
        super(embeddedValueResolver);
        super.addConverter(new EnumToStringConverter());
        super.addConverter(new StringToEnumConverter());
    }

    /**
     * Return a shared default application {@code ConversionService} instance, lazily
     * building it once needed.
     * <p>
     * Note: This method actually returns an {@link HrmConversionService}
     * instance. However, the {@code ConversionService} signature has been preserved for
     * binary compatibility.
     * @return the shared {@code BladeConversionService} instance (never{@code null})
     */
    public static GenericConversionService getInstance() {
        HrmConversionService sharedInstance = HrmConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (HrmConversionService.class) {
                sharedInstance = HrmConversionService.SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new HrmConversionService();
                    HrmConversionService.SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }

}
