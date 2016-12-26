## 进度监控步骤
# 第一步：抓取本地CloudRender文件夹目录
# 第二步：在01.Model中获取款号存入progress_control，并在款号目录下读取文件名称带有_02的csv文件，以获取这次渲染团片总数
# 第三步：调maya渲染脚本，渲染结果保存道02.Render（24帧Alpha、24帧Color）
# 第四步：调用图片拼接功能(JAVA)，拼接结果保存到03.Joint(AO->SHADOW\yyy_Full.png、xxx_Color->PART\xxx\yyy_Full.png)
# 第五步：调用图片压缩脚本，将压缩结果保存到04.Compress（1、24帧[AO->SHADOW、xxx_Color->PART\xxx]，2、Full）
# 第六步：调用OSS上传功能(JAVA)，将结果保存到05.Upload（1、24帧，2、Full）
# 第七步：调用数据库接口(JAVA)，上传文件信息(URL)到数据库，将结果保存到06.Database（仅上传Full）
# 第八步：将01.Model款号文件夹移动到07.Done

## 过程
## 定时任务进行控制progress_control(当前步骤)
# 1.检查每个步骤，将信息保存到progress_steps中
# 2.判断完成的时候执行下一步
## 启动消息机制
# rabbitmq-plugins enable rabbitmq_management