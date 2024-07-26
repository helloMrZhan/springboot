package com.zjq.knife4j.controller.dept;

import com.zjq.knife4j.entity.Dept;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/depts")
@Api(value = "部门管理", description = "系统中的部门操作")
public class DeptController {

    /**
     * 初始化一个部门集合
     */
    private List<Dept> DeptList = Arrays.asList(
            new Dept(1, "总经办"),
            new Dept(2, "董事会"),
            new Dept(3, "研发中心")
    );
    

    @PostMapping
    @ApiOperation(value = "创建部门", notes = "创建一个新的部门")
    public ResponseEntity<String> createDept(@RequestBody @ApiParam(value = "部门信息", required = true) Dept Dept) {
        DeptList.add(Dept);
        return ResponseEntity.ok("创建成功");
    }

    @GetMapping
    @ApiOperation(value = "获取所有部门", notes = "返回的所有部门列表")
    public ResponseEntity<List<Dept>> getAllDepts() {
        return ResponseEntity.ok(DeptList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取部门", notes = "根据提供的部门ID返回单一部门")
    public ResponseEntity<Dept> getDeptById(@PathVariable("id") @ApiParam(value = "部门ID", required = true) Integer id) {
        //通过部门ID获取部门
        Dept Dept = DeptList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        return ResponseEntity.ok(Dept);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新部门", notes = "根据提供的部门ID更新部门信息")
    public ResponseEntity<String> updateDept(@PathVariable("id") @ApiParam(value = "部门ID", required = true) Integer id,
                                           @RequestBody @ApiParam(value = "部门信息", required = true) Dept Dept) {
        //通过部门ID修改部门
        DeptList.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(u -> {
            u.setDeptname(Dept.getDeptname());
        });
        return ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门", notes = "根据提供的部门ID删除部门")
    public ResponseEntity<String> deleteDept(@PathVariable("id") @ApiParam(value = "部门ID", required = true) Integer id) {
        //通过部门ID删除部门
        DeptList.removeIf(u -> u.getId().equals(id));
        return ResponseEntity.ok("删除成功");
    }

    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query",dataType="int",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", paramType = "query",dataType="int",defaultValue = "10"),
    })
    @ApiResponses({
            @ApiResponse(code=10001,message="xx业务规则不符合")
    })
    @ApiOperation(value = "获取分页查询部门", notes = "分页查询部门列表")
    public ResponseEntity<List<Dept>> getPageDepts(Integer page,Integer pageSize) {
        return ResponseEntity.ok(DeptList);
    }
}