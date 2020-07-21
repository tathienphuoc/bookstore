package SGU.BookStore.Model;

import SGU.BookStore.Entity.Role;
import SGU.BookStore.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class RoleModel {
    //tự động inject các bean cần thiết
    @Autowired
    RoleRepo roleRepo;
    //trả về mảng các quyền có trong CSDL
    public List<Role> getAllRole()
    {
        List<Role> roles = (List<Role>) roleRepo.findAll();
        //có tồn tại phần tử trong bản ROLE
        if(roles.size() > 0) {
            return roles;
        } else {
            return null;//trả về mảng rỗng
        }
    }

    //tạo mới hoặc cập nhật quyền
    public Role createOrUpdate(Role entity)
    {
        //tạo mới
        //chưa có id -> không tồn tại trong CSDL
        if(entity.getId()  == null)
        {
            entity = roleRepo.save(entity);//Khởi tạo
            return entity;//trả về đối tượng tương ứng trong CSDL(có id)
        }
        //cập nhật
        //có id
        else
        {
            Optional<Role> employee = roleRepo.findById(entity.getId());
            //Tìm thấy quyền có id là Id
            if(employee.isPresent())
            {
                Role newEntity = employee.get();
                newEntity.setName(entity.getName());

                newEntity = roleRepo.save(newEntity);//Cập nhật

                return newEntity;
            } else {
                //đối tượng không tồn tại trong bảng quyền
                //đã được đặt id vì lý do nào đó
                entity = roleRepo.save(entity);//Khởi tạo

                return entity;//trả về đối tượng vừa được khởi tạo
            }
        }
    }

    //trả về đối tượng dựa trên id
    public Role getRole(Integer Id)
    {
        Optional<Role> role=roleRepo.findById(Id);
        return role.isPresent()?role.get():null;//trả về null nếu không tìm thấy
    }

    //tạo 2 quyền mặc định
    public void createDefault() {
        //có tên là ADMIN
        Role adminRole = findByName("ADMIN");
        //và SER
        Role userRole = findByName("USER");
        //nếu chưa tồn tại
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ADMIN");
            createOrUpdate(adminRole);
        }
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            createOrUpdate(userRole);
        }
    }

    //trả về đối tượng dựa trên tên quyền
    public Role findByName(String s) {
        Optional<Role> role=roleRepo.findByName(s);
        return role.isPresent()?role.get():null;//trả về null nếu không tìm thấy
    }

    public Role getBook(Integer Id)
    {
        Optional<Role> role=roleRepo.findById(Id);
        return role.isPresent()?role.get():null;//trả về null nếu không tìm thấy
    }
}
