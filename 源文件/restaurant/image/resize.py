from PIL import Image
import os
def img_resize(file,w,h,type,suffix):
    img= Image.open(file)
    Resized=img.resize((w,h),Image.ANTIALIAS)
    Resized.save('resized.'+suffix,type,quality=90)

print("图片尺寸修改程序\n")
name = input("请输入要处理的图片文件名(包括文件后缀,暂时只支持jpg,png)\n")
width=input("请输入处理后宽度\n")
height=input("请输入处理后高度\n")
suffix=name.split('.')[1]
if suffix=='jpg':
    type='JPEG'
elif suffix=='png':
    type='PNG'

    
img_resize("./"+name,int(width),int(height),type,suffix)
print("图片修改好了!\n")
