package cn.iocoder.yudao.module.hospital.service.medicine;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.medicine.MedicineDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 药品信息 Service 接口
 *
 * @author 芋道源码
 */
public interface MedicineService {

    /**
     * 创建药品信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMedicine(@Valid MedicineSaveReqVO createReqVO);

    /**
     * 更新药品信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMedicine(@Valid MedicineSaveReqVO updateReqVO);

    /**
     * 删除药品信息
     *
     * @param id 编号
     */
    void deleteMedicine(Integer id);

    /**
    * 批量删除药品信息
    *
    * @param ids 编号
    */
    void deleteMedicineListByIds(List<Integer> ids);

    /**
     * 获得药品信息
     *
     * @param id 编号
     * @return 药品信息
     */
    MedicineDO getMedicine(Integer id);

    /**
     * 获得药品信息分页
     *
     * @param pageReqVO 分页查询
     * @return 药品信息分页
     */
    PageResult<MedicineDO> getMedicinePage(MedicinePageReqVO pageReqVO);

}