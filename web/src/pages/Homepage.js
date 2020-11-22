import React from 'react'
import ProductCard from '../components/ProductCard'
import Carousel from '../components/Carousel'

import DefaultProductImg from '../images/default-product-image.png'
import SampleDealsBrand from '../images/sample-deals-brand.jpg'
import SampleDealsBrand2 from '../images/sample-deals-brand2.jpg'

const items = [
    {
        name: 'product 1',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 2',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 3',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 3',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 3',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 3',
        img: DefaultProductImg,
        price: '$7.99'
    },
    {
        name: 'product 3',
        img: DefaultProductImg,
        price: '$7.99'
    }
]

const Homepage = () => {
    return (
        <div>
            <div className='p-5 deals container-fluid' style={{backgroundColor: 'var(--red-main)'}}>
                <div className='row'>
                    <div className='col-md p-3'>
                        <img className='rounded-lg img-fluid' src={SampleDealsBrand} alt='deal brand'/>
                    </div>
                    <div className='col-md p-3'>
                        <img className='rounded-lg img-fluid' src={SampleDealsBrand2} alt='deal brand'/>
                    </div>
                </div>
            </div>
            <div className='p-5'>
                <Carousel items={items} title="Best Sellers" />
            </div>
        </div>
    )
}

export default Homepage