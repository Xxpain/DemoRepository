//存放主要交互逻辑
//js模块化
//可以看出 java包 下面的类  下面的方法，例如seckill.detail.init(params)
var seckill={
		//封装秒杀相关ajax的url
		URL:{
			now:function(){
				return "/demo/seckill/time/now";
			},
			exposer:function(seckillId){
				return "/demo/seckill/"+seckillId+"/exposer";
			},
			execution:function(seckillId,md5){
				return '/demo/seckill/'+seckillId+'/'+md5+'/execution';
			}
		},
		handleSeckill:function(seckillId,node){
			node.hide()
			.html('<buttion class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//给按钮
			$.post(seckill.URL.exposer(seckillId),{},function(result){
				//在回调函数中执行交互流程
				if(result && result['success']){
					var exposer = result['data'];
					if(exposer['exposed']){
						//开启秒杀
						//获取秒杀地址
						var md5 =exposer['md5'];
						var killUrl = seckill.URL.execution(seckillId,md5);
						//alert("秒杀地址killUrl:"+killUrl);
						//秒杀点击事件  只能点击1次
						$('#killBtn').one('click',function(){
							//绑定按钮的秒杀操作
							//先禁用按钮
							$(this).addClass('disabled');
							//发送秒杀请求 执行秒杀
							$.post(killUrl,{},function(result){
								if(result && result['success']){
									var killResult = result["data"];
									var state = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									//alert("stateInfo::"+stateInfo);
									//3：显示秒杀结果
									node.html('<span class="label label-success">'+stateInfo+'</span>');
								}
							});
						});
						node.show();
					}else{
						//未开始秒杀
						var now =exposer['now'];
						var start =exposer['start'];
						var end =exposer['end'];
						//重新计算计时逻辑
						seckill.countdown(seckillId,now,start,end);
					}
				}else{
					alert("警告——>result:"+result);
				}
			});
		},
		//验证手机号
		validatePhone:function(phone){
			if(phone && phone.length==11 && !isNaN(phone)){
				//alert("手机号验证通过!");
				return true;
			}else{
				return false;
			}
		},
		countdown:function(seckillId,nowTime,startTime,endTime){
			var seckillbox = $("#seckill-box");
			//alert("nowTimenowTime"+nowTime);
			if(nowTime>endTime){
				seckillbox.html('秒杀已经结束了!');
			}else if(nowTime<startTime){
				//秒杀未开始 计时时间绑定
				startTime=parseInt(startTime,10);//把字符串转成int型
				var killTime= new Date(startTime);
				//alert(killTime);
				seckillbox.countdown(killTime,function(event){
					//时间格式
					var format = event.strftime('秒杀倒计时:%D天 %H时 %M分 %S秒');
					seckillbox.html(format);
					//时间倒计时以后完成的事件
				}).on('finish.countdown',function(){
					//获取秒杀地址  控制实现逻辑 执行秒杀按钮
					//seckillbox为span的节点
					seckill.handleSeckill(seckillId,seckillbox);
				});
			}else{
				//秒杀开始
				seckill.handleSeckill(seckillId,seckillbox);
			}
		},
		//详情页秒杀逻辑
		detail:{
			//详情页初始化
			init:function(params){
				//用户手机验证和登录 计时交互
				//规划我们的交互流程
				//在cookie查找手机号
				var killPhone= $.cookie("killPhone");
				//验证手机号
				if(!seckill.validatePhone(killPhone)){
					//绑定phone
					//控制输出
					var killPhoneModal = $("#killPhoneModal");
					killPhoneModal.modal({
						//显示弹出层
						show:true,
						backdrop:'static',//禁止位置关闭
						keyboard:false  //关闭键盘事件。这样就无法关闭遮罩
					});
					$("#killPhoneBtn").click(function(){
						var inputPhone = $('#killPhoneKey').val();//得到用户输入的手机
						if(seckill.validatePhone(inputPhone)){
							//alert("进来了");
							//电话写入cookie,,后面7代表有效期为7天，然后path为 cookie在这个路径下有效
							$.cookie('killPhone',inputPhone,{expires:7,path:'/demo/seckill'});
							//刷新页面
							window.location.reload();
						}else{
							//先隐藏 在放内容 然后再显示
							$('#killPhoneMessage').html("<label class='label label-danger'>手机号错误-></label>").show(300);
						}
					});
				}
				//已经登录了
				//计时交互
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				var seckillId = params['seckillId'];
				$.get(seckill.URL.now(),{},function(result){
					if(result && result['success']){
						var nowTime = result['data'];
						seckill.countdown(seckillId,nowTime,startTime,endTime);
					}else{
						console.log('result:'+result);
					}
				});

			}
		}
}
