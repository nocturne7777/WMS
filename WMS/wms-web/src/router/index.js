import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: '/login',
        component: () => import('../components/LoginPage.vue')
    },

    {
        path: '/PageIndex',
        name: 'PageIndex',
        component: () => import('../components/PageIndex.vue'),
        children:[
            {
                path:'/HomePage',
                name:'HomePage',
                meta :{ title: '首页' },

            component:()=> import('../components/HomePage.vue')
            },
            // {
            //     path:'/adminManager',
            //     name:'admin',
            //     meta :{ title: '管理管理页面' },
            //
            //     component:()=> import('../components/admin/adminManager.vue')
            // },
            // {
            //     path:'/userManager',
            //     name:'user',
            //     meta :{ title: '用户管理页面' },
            //
            //     component:()=> import('../components/user/userManager.vue')
            // },

        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode: 'history',
        routes
    }).matcher;
}

export function addNewRoute(menuList) {
    let routes = router.options.routes;
    console.log(routes);
    routes.forEach(routeItem => {
        if (routeItem.path == "/PageIndex") {
            menuList.forEach(menu => {
                let childRoute = {
                    path: '/' + menu.menuclick,
                    name: menu.menuname,
                    meta: { title: menu.menuname },
                    component: () => import('../components/' + menu.menucomponent)
                };
                routeItem.children.push(childRoute);
            });
        }
    });
    resetRouter();
    router.addRoutes(routes);
}

export default router;