(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d7cda3c"],{"057f":function(t,e,r){var n=r("fc6a"),o=r("241c").f,i={}.toString,a="object"==typeof window&&window&&Object.getOwnPropertyNames?Object.getOwnPropertyNames(window):[],c=function(t){try{return o(t)}catch(e){return a.slice()}};t.exports.f=function(t){return a&&"[object Window]"==i.call(t)?c(t):o(n(t))}},5530:function(t,e,r){"use strict";r.d(e,"a",(function(){return i}));r("a4d3"),r("4de4"),r("4160"),r("e439"),r("dbb4"),r("b64b"),r("159b");function n(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function o(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function i(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?o(Object(r),!0).forEach((function(e){n(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}},6385:function(t,e,r){"use strict";var n=r("8b1c"),o=r.n(n);o.a},"746f":function(t,e,r){var n=r("428f"),o=r("5135"),i=r("e538"),a=r("9bf2").f;t.exports=function(t){var e=n.Symbol||(n.Symbol={});o(e,t)||a(e,t,{value:i.f(t)})}},8418:function(t,e,r){"use strict";var n=r("c04e"),o=r("9bf2"),i=r("5c6c");t.exports=function(t,e,r){var a=n(e);a in t?o.f(t,a,i(0,r)):t[a]=r}},"8b1c":function(t,e,r){},a4d3:function(t,e,r){"use strict";var n=r("23e7"),o=r("da84"),i=r("d066"),a=r("c430"),c=r("83ab"),f=r("4930"),u=r("fdbf"),s=r("d039"),l=r("5135"),b=r("e8b5"),p=r("861d"),d=r("825a"),m=r("7b0b"),g=r("fc6a"),v=r("c04e"),y=r("5c6c"),h=r("7c73"),w=r("df75"),O=r("241c"),j=r("057f"),P=r("7418"),S=r("06cf"),$=r("9bf2"),k=r("d1e7"),E=r("9112"),N=r("6eeb"),z=r("5692"),A=r("f772"),Z=r("d012"),x=r("90e3"),D=r("b622"),_=r("e538"),J=r("746f"),q=r("d44e"),C=r("69f3"),F=r("b727").forEach,T=A("hidden"),U="Symbol",I="prototype",Q=D("toPrimitive"),W=C.set,B=C.getterFor(U),G=Object[I],H=o.Symbol,K=i("JSON","stringify"),L=S.f,M=$.f,R=j.f,V=k.f,X=z("symbols"),Y=z("op-symbols"),tt=z("string-to-symbol-registry"),et=z("symbol-to-string-registry"),rt=z("wks"),nt=o.QObject,ot=!nt||!nt[I]||!nt[I].findChild,it=c&&s((function(){return 7!=h(M({},"a",{get:function(){return M(this,"a",{value:7}).a}})).a}))?function(t,e,r){var n=L(G,e);n&&delete G[e],M(t,e,r),n&&t!==G&&M(G,e,n)}:M,at=function(t,e){var r=X[t]=h(H[I]);return W(r,{type:U,tag:t,description:e}),c||(r.description=e),r},ct=u?function(t){return"symbol"==typeof t}:function(t){return Object(t)instanceof H},ft=function(t,e,r){t===G&&ft(Y,e,r),d(t);var n=v(e,!0);return d(r),l(X,n)?(r.enumerable?(l(t,T)&&t[T][n]&&(t[T][n]=!1),r=h(r,{enumerable:y(0,!1)})):(l(t,T)||M(t,T,y(1,{})),t[T][n]=!0),it(t,n,r)):M(t,n,r)},ut=function(t,e){d(t);var r=g(e),n=w(r).concat(dt(r));return F(n,(function(e){c&&!lt.call(r,e)||ft(t,e,r[e])})),t},st=function(t,e){return void 0===e?h(t):ut(h(t),e)},lt=function(t){var e=v(t,!0),r=V.call(this,e);return!(this===G&&l(X,e)&&!l(Y,e))&&(!(r||!l(this,e)||!l(X,e)||l(this,T)&&this[T][e])||r)},bt=function(t,e){var r=g(t),n=v(e,!0);if(r!==G||!l(X,n)||l(Y,n)){var o=L(r,n);return!o||!l(X,n)||l(r,T)&&r[T][n]||(o.enumerable=!0),o}},pt=function(t){var e=R(g(t)),r=[];return F(e,(function(t){l(X,t)||l(Z,t)||r.push(t)})),r},dt=function(t){var e=t===G,r=R(e?Y:g(t)),n=[];return F(r,(function(t){!l(X,t)||e&&!l(G,t)||n.push(X[t])})),n};if(f||(H=function(){if(this instanceof H)throw TypeError("Symbol is not a constructor");var t=arguments.length&&void 0!==arguments[0]?String(arguments[0]):void 0,e=x(t),r=function(t){this===G&&r.call(Y,t),l(this,T)&&l(this[T],e)&&(this[T][e]=!1),it(this,e,y(1,t))};return c&&ot&&it(G,e,{configurable:!0,set:r}),at(e,t)},N(H[I],"toString",(function(){return B(this).tag})),N(H,"withoutSetter",(function(t){return at(x(t),t)})),k.f=lt,$.f=ft,S.f=bt,O.f=j.f=pt,P.f=dt,_.f=function(t){return at(D(t),t)},c&&(M(H[I],"description",{configurable:!0,get:function(){return B(this).description}}),a||N(G,"propertyIsEnumerable",lt,{unsafe:!0}))),n({global:!0,wrap:!0,forced:!f,sham:!f},{Symbol:H}),F(w(rt),(function(t){J(t)})),n({target:U,stat:!0,forced:!f},{for:function(t){var e=String(t);if(l(tt,e))return tt[e];var r=H(e);return tt[e]=r,et[r]=e,r},keyFor:function(t){if(!ct(t))throw TypeError(t+" is not a symbol");if(l(et,t))return et[t]},useSetter:function(){ot=!0},useSimple:function(){ot=!1}}),n({target:"Object",stat:!0,forced:!f,sham:!c},{create:st,defineProperty:ft,defineProperties:ut,getOwnPropertyDescriptor:bt}),n({target:"Object",stat:!0,forced:!f},{getOwnPropertyNames:pt,getOwnPropertySymbols:dt}),n({target:"Object",stat:!0,forced:s((function(){P.f(1)}))},{getOwnPropertySymbols:function(t){return P.f(m(t))}}),K){var mt=!f||s((function(){var t=H();return"[null]"!=K([t])||"{}"!=K({a:t})||"{}"!=K(Object(t))}));n({target:"JSON",stat:!0,forced:mt},{stringify:function(t,e,r){var n,o=[t],i=1;while(arguments.length>i)o.push(arguments[i++]);if(n=e,(p(e)||void 0!==t)&&!ct(t))return b(e)||(e=function(t,e){if("function"==typeof n&&(e=n.call(this,t,e)),!ct(e))return e}),o[1]=e,K.apply(null,o)}})}H[I][Q]||E(H[I],Q,H[I].valueOf),q(H,U),Z[T]=!0},a581:function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"signUp"},[r("el-form",{ref:"form",attrs:{model:t.form,rules:t.rules,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"邮箱：",prop:"email"}},[r("el-input",{attrs:{placeholder:"Email"},model:{value:t.form.email,callback:function(e){t.$set(t.form,"email",e)},expression:"form.email"}})],1),r("el-form-item",{attrs:{label:"用户名：",prop:"userName"}},[r("el-input",{attrs:{placeholder:"UserName"},model:{value:t.form.userName,callback:function(e){t.$set(t.form,"userName",e)},expression:"form.userName"}})],1),r("el-form-item",{attrs:{label:"密码：",prop:"password"}},[r("el-input",{attrs:{type:"password",placeholder:"Password"},model:{value:t.form.password,callback:function(e){t.$set(t.form,"password",e)},expression:"form.password"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"warning"},on:{click:t.onSubmit}},[t._v("注册账号")])],1)],1)],1)},o=[],i=r("5530"),a=r("2f62"),c={data:function(){return{form:{},rules:{email:[{required:!0,validator:function(t,e,r){/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(e)?r():r(new Error("请输入正确的邮箱"))},trigger:"blur"}],userName:[{required:!0,validator:function(t,e,r){/^[a-zA-Z0-9_-]{4,16}$/.test(e)?r():r(new Error("请输入4-16位字母、数字、下划线组成的用户名"))},trigger:"blur"}],password:[{required:!0,validator:function(t,e,r){/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(e)?r():r(new Error("请输入6-20位数字或字母的密码"))},trigger:"blur"}]}}},methods:Object(i["a"])(Object(i["a"])({},Object(a["b"])(["register"])),{},{onSubmit:function(){var t=this;this.$refs.form.validate((function(e){if(!e)return!1;t.register(t.form).then((function(e){200===e.data.code&&t.$router.push("/sign/in")}))}))}})},f=c,u=(r("6385"),r("2877")),s=Object(u["a"])(f,n,o,!1,null,"4692e732",null);e["default"]=s.exports},b64b:function(t,e,r){var n=r("23e7"),o=r("7b0b"),i=r("df75"),a=r("d039"),c=a((function(){i(1)}));n({target:"Object",stat:!0,forced:c},{keys:function(t){return i(o(t))}})},dbb4:function(t,e,r){var n=r("23e7"),o=r("83ab"),i=r("56ef"),a=r("fc6a"),c=r("06cf"),f=r("8418");n({target:"Object",stat:!0,sham:!o},{getOwnPropertyDescriptors:function(t){var e,r,n=a(t),o=c.f,u=i(n),s={},l=0;while(u.length>l)r=o(n,e=u[l++]),void 0!==r&&f(s,e,r);return s}})},e439:function(t,e,r){var n=r("23e7"),o=r("d039"),i=r("fc6a"),a=r("06cf").f,c=r("83ab"),f=o((function(){a(1)})),u=!c||f;n({target:"Object",stat:!0,forced:u,sham:!c},{getOwnPropertyDescriptor:function(t,e){return a(i(t),e)}})},e538:function(t,e,r){var n=r("b622");e.f=n}}]);
//# sourceMappingURL=chunk-3d7cda3c.f7166e20.js.map