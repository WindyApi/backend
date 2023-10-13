package top.whiteleaf03.api.dubbo.interfaceinfo;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.modal.dto.InterfaceIdAndStatusDTO;

/**
 * @author WhiteLeaf03
 */
@Service
@DubboService
public class InterfaceInfoDubboServiceImpl implements InterfaceInfoDubboService {
    private final InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    public InterfaceInfoDubboServiceImpl(InterfaceInfoMapper interfaceInfoMapper) {
        this.interfaceInfoMapper = interfaceInfoMapper;
    }

    /**
     * 根据路径查询接口id和状态
     *
     * @param url 接口路径
     * @return 返回结果
     */
    @Override
    public InterfaceIdAndStatusDTO selectIdAndStatusByUrl(String url) {
        return interfaceInfoMapper.selectIdAndStatusByUrl(url);
    }
}
